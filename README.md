# Getting Started

## Requests Manager

This application allows to manage requests.

### Creation

You cna create a new request with title and content.

### Update

When request is CREATED or VERIFIED you have a possibility to update the content of your request.

### Statuses

After creation request has a CREATED status.
As next step, the request has to be verified, accepted and then there is possibility to publish it.
After publishing, the request has generated a unique number.
You can delete your request after creation.
The request can be rejected after verification and after acceptation.

### History

After each status change, there is created am history entry. Requests history contains data about a previous status and a current status.
If request is rejected or deleted, history will contain additional information about the reason.

## Start application

Application is configured for H2 database with in-memory location, so after compilation you can just run the application.
