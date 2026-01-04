#!/bin/bash

echo "=== Train Booking System ==="
echo "This script runs the IRCTC train booking application."
echo ""

# Build the project
echo "Building project..."
./gradlew build installDist

if [ $? -eq 0 ]; then
    echo ""
    echo "✓ Build successful!"
    echo ""
    echo "=== Running automatic test ==="
    ./gradlew runAutoTest
    
    echo ""
    echo "=== To run the application interactively ==="
    echo "Use this command:"
    echo "./app/build/install/app/bin/app"
    echo ""
    echo "Or run directly with:"
    echo "./gradlew run"
    echo ""
    echo "Note: The application will wait for user input when run interactively."
else
    echo "Build failed!"
    exit 1
fi