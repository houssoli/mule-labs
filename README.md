# Confluex Mule Extensions

This is a library of extension to Mule code. It contains utility code for testing, etc.

# Warning

This library is still under heavy development. Feel free to use, contribute but there could be changes to
the API until it reaches 1.0 status.

# Modules

The Confluex Mule Extension is composed of several modules:

## confluex-test-http

Mock HTTP testing library for mocking out interaction to HTTP endpoints from within Mule FunctionalTestCase (although
it could be used without mule at all).

## confluex-test-notifications

Since the MuleFunctionalTest runs in a separate thread from the server, it's often
difficult to determine when the messages are finished and when to start your assertions (usually by waiting or
sending the messages to a mock endpoint).

This module contains event listeners for endpoints which you can use to block until the required messages have
passed before making your assertions.

# Installing

We're actively working on getting this project into the maven central repository, you will have to build it from
github. Just check it out and run mvn install.