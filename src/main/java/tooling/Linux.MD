Linux File System:

File name in Linux support capital cases. Such File and fIle are different file names.

FHS: filesystem hierarchy standard.

/boot
All the Linux OS boot programs, don't mess up this folder.

/bin
All the binary exe, such cat to view a file.
/sbin
System binary, which only system admin have access to.

/dev
This where all the devices live. Hardware, disk, usb, webcam, keyboard etc.

/etc
Various configuration, extension etc...

/lib (lib32, lib64)
Required by program in bin.

/media /mnt
Mount media

/opt
Optional folder, usually program installed here.
Also application created by yourslef should be istalled here.

/proc
The process current running. enter pid will see the detail
cat /proc/uptime

/root
Root user's home folder. Need root permission to access it.

/run
/sys
Runtime, no file stored here.

/snap

/srv
Servers, such as web server, where files can be stored for external users. 

/tmp
Temp folder for snapshot data from application.

/usr
User application folder, specific user application.

/var
Variable directory. var/log: log files; var/crash: crash info.

/home
Personal folders, user only access their own folder.

How to check the kernel version of a Linux system?
uname -a

How to see the current IP address on Linux?
ifconfig

How to check for free disk space in Linux?
df -ah

How to see if a Linux service is running?
service udev status
systemctl status udev

How to check the size of a directory in Linux?
du -sh codedir

How to check for open ports in Linux?
netstat
netstat -tulpn
sudo netstat -tulpn  (run as root, will also show the process id PID and programe name)

How to check Linux process information (CPU usage, memory, user information, etc.)?
1). px aus | grep nginx
2). top
3). htop

How to deal with mounts in Linux
ls /mnt
mount /dev/sda2 /mnt

Checking existing mount:
mount

How do you look up something? (main page)
man


Top 10 linux command:
