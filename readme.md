To create image:
docker build -t [imageName] .
To run container
docker run --name [containerName] -it -p 8080:8080 [imageName]:latest