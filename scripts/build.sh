#!/bin/sh

private_key_path="snowflake_rsa_key.pem"
echo $private_key_path
if [ -z "$1" ]; then
  echo "No account provided, please provide a valid Snowflake account"
  exit 1
fi

if [ -z "$2" ]; then
  echo "No username provided, please provide a valid Snowflake username"
  exit 1
fi

if [ -z "$3" ]; then
  echo "No Finnhub token provided, please provide a valid Finnhub token"
  exit 1
fi

if ! [ -f "$private_key_path" ]; then
  echo "No private key file found in workdir: $private_key_path"
  exit
fi

file_content=$(cat "$private_key_path")

docker build -t realtime-streaming-kafka -f ../kafka.Dockerfile --progress=plain --build-arg ACCOUNT="$1" --build-arg USERNAME="$2" --build-arg PRIVATE_KEY="$file_content" .
# cd level below because problem with copying in dockerfile
cd ..
docker build -t realtime-streaming-app -f app.Dockerfile --progress=plain --build-arg ACCOUNT="$1" --build-arg USERNAME="$2" --build-arg FINNHUB_TOKEN="$3" --build-arg PRIVATE_KEY="$file_content" .
