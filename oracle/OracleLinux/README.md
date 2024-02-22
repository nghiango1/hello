# Cài đặt Oracle LINUX

VM
- ORL79
- Cấu hình: 4vCore 32 GB RAM 256 Storage
- Network:
    - External trước (Mạng public)
- OS: Oracle Linux
- Ok

Edit:
- Thêm ổ đĩa: Chọn bản cài ISO của OLR7_U9.iso từ Datastore Iso
- Bật máy ảo (Power On)

OS Installation:
- Thực hiện theo giao diện hướng dẫn
- Chọn ngày và giờ: Hochiminh-time
- Chọn phân vùng đĩa:
    - Chọn Manual để tự cấu hình phân vùng cho đĩa, tích vào: "I will config partitioning"
    - Chế độ: LVM -> Standard disk
    - Phân bổ như sau:
        ```
        <partition_table> 1K
        /var    32 GiB
        /home   12 GiB
        /swap   32 GiB
        /       32 GiB
        /tmp    12 GiB
        /u01    
        ```
- Network, Hostname: Bỏ qua
- Done~
- Tạo User:
    - Đặt mật khẩu cho root: <bất kỳ>
    - Tạo sẵn user:
        - Full name: <Bỏ trống>
        - username: oracle
        - pass: <bất kỳ>
- Chờ cài đặt hoàn thành
