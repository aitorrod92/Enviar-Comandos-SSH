# Enviar-Comandos-SSH
Simple command-line tool that send two SSH commands ("uptime" and "df -h") to multiple domains. In order to make it work:

1. Write the domains in a CSV file with the format "host;port;user;password". Use a row per domain.
2. Put the CSV in the same directory as the JAR (or in the root directory of the project).
3. Execute the program, confirm and wait. 
4. Open the text file with the responses (somewhat formatted) that has been created.

If any error happens, the program will let you know.
