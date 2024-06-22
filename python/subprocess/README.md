# Subprocess

These is some time I want more advance script to automating os related task. I prefer using python instead of normal bash script, this achiveable via setup the top of file script enviroment. Example can be found with-in `py-script`

```python
#!/usr/bin/env python

print("Py script said hello!")
```

Which can be run directly (we still need to setup exectuable flag before hand) without the need for calling it through `python` REPL
```bash
chmod +x py-script 
./py-script 
```

Expected output look like this
```
Py script said hello!
```

About subprocess: Subprocess module give a better way to acessing and controlling os command through python. This is crusal when I want to create a os task automation script as we are using python as the replacement for normal bash script, this is because that I have quite "mediocre" knowledge about bash variable/logic flow control.

## Notes

Subprocess normally use can capture the entire os command output, but is quite hard to print them out continously. To achived this behavior, we need to interacting with inner popen process. Here is example I can found how to do so in this [stack overflow thread](https://stackoverflow.com/questions/4417546/constantly-print-subprocess-output-while-process-is-running)

```python
import subprocess
import sys

def execute(command):
    subprocess.check_call(command, shell=True, stdout=sys.stdout, stderr=subprocess.STDOUT)
```

Which is great and simple enough.
