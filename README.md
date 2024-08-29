# PrintScript

PrintScript is a lightweight language inspired by TypeScript, designed specifically for printing operations. The project includes several modules to handle different aspects of code processing: a lexer, parser, interpreter, linter, and formatter. 

## Project Structure

- **Lexer:** Transforms the source code into meaningful tokens.
- **Parser:** Transforms the tokenized input into an Abstract Syntax Tree (AST). Checks for sintactic and semantic errors.
- **Interpreter:** Executes the code based on the AST.
- **Linter:** Analyzes the code for potential issues and enforces coding standards.
- **Formatter:** Formats the code to ensure consistent styling.

## CLI Commands

The following commands are available in the root directory and can be executed from any command line:

- `./validate` - **Usage:** `./validate [file]`  
  Validates the syntax of the specified PrintScript file. 

- `./execute` - **Usage:** `./execute [file]`  
  Executes the PrintScript code in the specified file and prints the result.

- `./analyze` - **Usage:** `./analyze [file] [rules Json]`  
  Analyzes the PrintScript code in the specified file for potential issues and generates a report.

- `./format` - **Usage:** `./format [file] [rules Json]`  
  Formats the PrintScript code in the specified file according to predefined style rules.
