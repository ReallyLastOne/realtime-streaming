#!/bin/sh

# the script generates a key pair necessary for connecting with Snowflake
# reference: https://docs.snowflake.com/en/user-guide/kafka-connector-install#using-key-pair-authentication-key-rotation

# generating private key
openssl genrsa -out snowflake_rsa_key.pem 2048

# generating public key
openssl rsa -in snowflake_rsa_key.pem -pubout -out snowflake_rsa_key.pub

echo "Deleting header and footer from public key..."
# delete header and footer from file
sed -i '/-----BEGIN PUBLIC KEY-----/d;/-----END PUBLIC KEY-----/d' snowflake_rsa_key.pub

echo "Removing newlines in public key..."
# transform to format that is required by Snowflake
sed -i ':a;N;$!ba;s/\n//g' snowflake_rsa_key.pub

echo "Done"