# DevUtils
Contains commonly used utility classes such as file loaders, validation checkers, and timers

This repository is used in my RPG-Engine and Construction Set project as well.

## MathString class!
The Mathstring class contains the function 'eval(String equation)' which takes a string, and attempts to evaluate it as a math equation. The only acceptable characters in the string are +, -, \*, /, ^, (, ), numbers, decimal points, and white space. It is currently working as expected, but needs to be tested more for bugs.

## TextValidator
The TextValidator class contains many staic functions to determine the contents of a string (or classify a character). Some of the functions include 'isAlphaNumeric(String s)', 'isAlpha(String s)' and 'isNumeric(String s)' which return booleans based on the passed in string's contents
