## Spring AI Temperature Service
A Spring Boot application that uses Spring AI to process natural language queries and provide real-time temperature information using a custom Function Calling Tool.

## Features
    • Natural Language Processing: Ask questions like "What is the temperature in London?"
    • Spring AI Function Calling: The AI automatically identifies when it needs temperature data and calls the temperature tool.
    • REST API: Simple endpoint to interact with the AI assistant.

## Calling
curl -X POST http://localhost:8080/simpleQuery
-H "Content-Type: application/json" 
-d '{"query": "what is current temperature of London?"}'
