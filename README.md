https://examples.javacodegeeks.com/android/core/socket-core/android-socket-example/

run server:
run on 2 device: 5554 is server, 5556 is client
terminal:
- nano ~/.emulator_console_auth_token to copy "token" (eg: naWEuKjI7jU/T94O)
- telnet localhost 5554
- auth "token"
- redir add tcp:5003:6003

stop server: control + ] => quit