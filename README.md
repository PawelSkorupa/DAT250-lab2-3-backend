# DAT250-Lab2 Report

## Overview
Objective of assignment: create a simple REST API using Spring framework, which supports simple CRUD operations for the domain entities for a PollApp.

## Step 1: Domain Model
Based on the diagram provided in assignment description, I implemented a model for the 4 domain entities.

## Step 2: Test Scenarios
Using Postman, I designed a few tests scenarios based on what had been proposed in assignment description:
- Create a new user
- List all users (-> shows the newly created user)
- Create another user
- List all users again (-> shows two users)
- User 1 creates a new poll
- List polls (-> shows the new poll)
- User 2 votes on the poll
- User 2 changes his vote
- List votes (-> shows the most recent vote for User 2)
- Delete the one poll
- List votes (-> empty)

## Step 3: Implementing logic in PollManager and controllers
After establishing the goals of the app by designing test scenarios, I implemented required logic in the PollManager Component class. Then, I created controller classes for each domain entity to access the PollManager methods via http endpoints.

## Step 4: Automate Testing
Using Spring's Rest Client, I implemented a few tests for each of the controller to ensure the proper behaviour of the endpoints.

## Step 5: Build Automation
I took advantage of the Github Action prepared for the previous assignment to automate gradle build and test tasks.

## Technical difficulties
I did not encounter any major problems during installation of the software, as most of it I had had already installed and ready to use. However, I tried to reduce Java's boilerplate code (getters, setters, no-args constructors) 
by using Lombok library and unfortunately, I couldn't make it work - during build process, I encountered an exception regarding issues with encoding of the files: "java.nio.charset.MalformedInputException: Input length = 1".
