#include <linux/init.h>
#include <linux/module.h>

MODULE_LICENSE("GPL2");

static int my_init(void) {
  printk(KERN_INFO "Hello world.\n");
  return 0;
}

static void my_exit(void) {
  printk(KERN_INFO "Goodbye world.\n");

  return;
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

module_init(my_init);
module_exit(my_exit);
