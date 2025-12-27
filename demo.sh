#!/bin/bash

echo "=== Running IRCTC Train Booking System ==="
echo "This demo shows the train search functionality working:"
echo ""
echo "Loading trains from JSON..."
echo ""

# Run the interactive test to show functionality
./gradlew runInteractiveTest

echo ""
echo "=== To run the full interactive system: ==="
echo "1. cd /home/utkarsh/dev/Java/IRCTC"
echo "2. ./gradlew run"
echo "3. Choose option 4 (Search Trains)"
echo "4. Enter source: bangalore"
echo "5. Enter destination: delhi"
echo ""
echo "The system will show available trains from the JSON file!"