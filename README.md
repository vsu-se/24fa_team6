# Setting the project up in your local IDE
Depending on your IDE, you may be able to clone the project from your IDE or have to clone it separately first.\
On IntelliJ, you click on the "Get from VCS" button > provide the repo URL (go to the repo homepage and click on the "Code" button then copy the HTTPS URL). This will open the project with all of the remote branches.

# Making Changes
Before you make any changes, be sure to checkout to your branch. You can do this via SourceTree, Cmndr, your terminal, inside your IDE, or whatever program you use.\
The branch structure is as follows:
```
main: the production ready, tested code
    development: the main development code where all of your code will be merged to before production
        lastname: where you will work
```
As you make changes in your branch, commit them. Once you get done with a task, push all of the commits. We can then create pull requests and review them.\
After we review code from a personal branch, we can merge them into development. This code all has to be tested before it can be merged into main.

## Commit Messages
Make sure to follow the messaging scheme in the "02_requirements" file provided to us. For example, if you create a new file: "ADD - created Employee.java to hold and handle all employee-related code".