# Hosting

Not that hard, just get your self a public ip.

- Normally, a hosting service like cloud will give you their VM server and you need to deploy your app there
- Some time, you want to use your own PC/Workstation/Laptop ... directly instead as the server already up and "work on my PC"

## Tunnering service - Ngrok

This help tunner a ip:port to a public accessable address provided by ngrok, but there is quite some limitation on Free-tier plan when it come to stablelizing our address (while for fun project could never pass 1Gb network through put anyway). As we want a relyable server which alway up and running even without human interaction, we can use a script to:

- Check network connection every 1s and start new tunner when network is available
- Create a systemd service so that our script automate start/restart itself

This help with the server part. Now to our client, as we can't control the address that will be assign by ngrok, we can use ngrok API it self to get the latest link of our server (cool right!).

Now we also can automate this process by publicly show our latest ngrok tunner link on a data sharing platform (facebook? twiter? youtube?) or we can just have a redirected server to help user goes to the right latest tunner link

This allow us to reuse our PC/Own's server compute resource to handle user request (we still rely on both ngrok network and provider internet network tho), as ~100-200$ laptop can out perform a lot of 20$/hour VPS plan
