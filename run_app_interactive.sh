#!/bin/bash

echo "Starting Train Booking System..."
echo "==============================="
cd /home/utkarsh/dev/Java/IRCTC

# Set the classpath properly
CLASSPATH="app/build/classes/java/main:app/build/libs/*"

# Find all required JAR files in Gradle cache
GRADLE_JARS=$(find ~/.gradle -name "*.jar" 2>/dev/null | tr '\n' ':')

# Combine classpaths
FULL_CLASSPATH="$CLASSPATH:$GRADLE_JARS"

# Run the application with proper input handling
exec java -cp "$FULL_CLASSPATH" org.example.App