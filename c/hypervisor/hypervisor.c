#include <linux/init.h>
#include <linux/module.h>

MODULE_LICENSE("GPL2");

#define X86_CR4_VMXE_BIT 13 /* enable VMX virtualization */
#define X86_CR4_VMXE _BITUL(X86_CR4_VMXE_BIT)

#define MSR_IA32_VMX_CR0_FIXED0 0x00000486
#define MSR_IA32_VMX_CR0_FIXED1 0x00000487
#define MSR_IA32_VMX_CR4_FIXED0 0x00000488
#define MSR_IA32_VMX_CR4_FIXED1 0x00000489

#define FEATURE_CONTROL_VMXON_ENABLED_OUTSIDE_SMX (1 << 2)
#define FEATURE_CONTROL_LOCKED (1 << 0)

#define EAX_EDX_VAL(val, low, high) ((low) | (high) << 32)
#define EAX_EDX_RET(val, low, high) "=a"(low), "=d"(high)
#define _ASM_EXTABLE_HANDLE(from, to, handler)                                 \
  .pushsection "__ex_table", "a";                                              \
  .balign 4;                                                                   \
  .long(from) -.;                                                              \
  .long(to) -.;                                                                \
  .long(handler) -.;                                                           \
  .popsection

static inline unsigned long long notrace __rdmsr1(unsigned int msr) {
  DECLARE_ARGS(val, low, high);

  asm volatile("1: rdmsr\n"
               "2:\n" _ASM_EXTABLE_HANDLE(1b, 2b, ex_handler_rdmsr_unsafe)
               : EAX_EDX_RET(val, low, high)
               : "c"(msr));

  return EAX_EDX_VAL(val, low, high);
}

#define MSR_IA32_FEATURE_CONTROL 0x0000003a
bool getVmxOperation(void) {
  unsigned long cr4;
  unsigned long required;
  unsigned long feature_control;
  // setting CR4.VMXE[bit 13] = 1
  asm volatile("mov %%cr4, %0" : "=r"(cr4) : : "memory");
  cr4 |= X86_CR4_VMXE;
  asm volatile("mov %0, %%cr4" : /* no output */ : "r"(cr4) : "memory");

  /*
   *  Configure IA32_FEATURE_CONTROL MSR to allow VMXON:
   *  Bit 0: Lock bit. If clear, VMXON causes a #GP.
   *  Bit 2: Enables VMXON outside of SMX operation. If clear, VMXON
   *  outside of SMX causes a #GP.
   */

  required = FEATURE_CONTROL_VMXON_ENABLED_OUTSIDE_SMX;
  required |= FEATURE_CONTROL_LOCKED;
  feature_control = __rdmsr1(MSR_IA32_FEATURE_CONTROL);
  printk(KERN_INFO "RDMS output is %ld", (long)feature_control);

  if ((feature_control & required) != required) {
    wrmsr(MSR_IA32_FEATURE_CONTROL, feature_control | required, low1);
  }

  /*
   * Ensure bits in CR0 and CR4 are valid in VMX operation:
   * - Bit X is 1 in _FIXED0: bit X is fixed to 1 in CRx.
   * - Bit X is 0 in _FIXED1: bit X is fixed to 0 in CRx.
   */
  __asm__ __volatile__("mov %%cr0, %0" : "=r"(cr0) : : "memory");
  cr0 &= __rdmsr1(MSR_IA32_VMX_CR0_FIXED1);
  cr0 |= __rdmsr1(MSR_IA32_VMX_CR0_FIXED0);
  __asm__ __volatile__("mov %0, %%cr0" : : "r"(cr0) : "memory");

  __asm__ __volatile__("mov %%cr4, %0" : "=r"(cr4) : : "memory");
  cr4 &= __rdmsr1(MSR_IA32_VMX_CR4_FIXED1);
  cr4 |= __rdmsr1(MSR_IA32_VMX_CR4_FIXED0);
  __asm__ __volatile__("mov %0, %%cr4" : : "r"(cr4) : "memory");
}

// looking for CPUID.1:ECX.VMX[bit 5] = 1
bool vmxSupport(void) {
  int getVmxSupport, vmxBit;
  __asm__("mov $1, %rax");
  __asm__("cpuid");
  __asm__("mov %%ecx , %0\n\t" : "=r"(getVmxSupport));
  vmxBit = (getVmxSupport >> 5) & 1;
  if (vmxBit == 1) {
    return true;
  } else {
    return false;
  }
}

static int my_init(void) {
  printk(KERN_INFO "Hello world.\n");
  if (vmxSupport()) {
    printk(KERN_INFO "This machine do support VMX.\n");
  } else {
    printk(KERN_INFO "This machine do not support VMX.\n");
  }
  return 0;
}

static void my_exit(void) {
  printk(KERN_INFO "Goodbye world.\n");

  return;
}

module_init(my_init);
module_exit(my_exit);
