# `lsof`

This is a ultility command which "list (all process that) open files".

## Notes

An open file may be a regular file, a directory, a block special  file,  a  character  special file, an executing text reference, a library, a stream or a network file (Internet socket, NFS file or UNIX domain socket.)

Network checking
- `lsof -ti :4000` check all process that listenning on port 4000 (may require right user permission if the process isn't owned by operator user)

Combining use
- Find process infomation that listen on port :4000
    ```sh
    ps -as | grep $(lsof -ti :4000)
    ```
- Kill process that listen on port :4000
    ```sh
    kill $(lsof -ti :4000)
    ```
