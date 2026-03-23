# Use a base image with Java 17 and Maven
FROM maven:3.9.6-eclipse-temurin-17

# Install Google Chrome and dependencies (Adding python3 for the local web server)
RUN apt-get update && apt-get install -y \
    wget \
    gnupg \
    unzip \
    python3 \
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

# Expose port 8080 for the Allure Report server
EXPOSE 8080

# Set the default command to run tests, generate Allure report, serve it, and print link
CMD ["bash", "-c", "mvn -B -e test -Dheadless=true; \
    echo ''; \
    echo '================================================================'; \
    echo '⏳ Generating Allure HTML Report...'; \
    mvn allure:report; \
    echo ''; \
    echo '✅ TESTS & REPORT COMPLETE!'; \
    echo '👉 CLICK THIS LINK TO OPEN YOUR DASHBOARD IN SECONDS:'; \
    echo 'http://localhost:8080'; \
    echo '*(To stop the server, simply stop the Container in Docker Desktop)*'; \
    echo '================================================================'; \
    echo ''; \
    python3 -m http.server 8080 --directory target/site/allure-maven-plugin"]
