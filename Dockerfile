# Use a base image with Java 17 and Maven
FROM maven:3.9.6-eclipse-temurin-17

# Install Google Chrome and dependencies
RUN apt-get update && apt-get install -y \
    wget \
    gnupg \
    unzip \
    && wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list' \
    && apt-get update \
    && apt-get install -y google-chrome-stable \
    && rm -rf /var/lib/apt/lists/*

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Pre-fetch dependencies to speed up subsequent builds
RUN mvn dependency:go-offline -B

# Set the default command to run tests (can be overridden)
CMD ["mvn", "-B", "-e", "test"]
