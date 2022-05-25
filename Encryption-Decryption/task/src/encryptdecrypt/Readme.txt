 Stage 1/6: Encrypted!
 
 Description
For the first stage, you need to manually encrypt the message "we found a treasure!" and print only the ciphertext (in lower case).
To encrypt the message, replace each letter with the letter that is in the corresponding position from the end of the English alphabet (a→z, b→y, c→x, ... x→c, y →b, z→a). Do not replace spaces or the exclamation mark.

Use the given template to your program to print the ciphertext instead of the original message.

The output should look like ## ##### # ########! where # is a lower-case English letter.




Stage 2/6: Knowledge is key
Description
Write a program that reads an English message and an integer number (key) from the standard input and shifts each letter by the specified number according to its order in the alphabet. If you reach the end of the alphabet, start back at the beginning (a follows z).

The English alphabet is below:

abcdefghijklmnopqrstuvwxyz
The program should not modify non-letter characters.

The key is assumed to mean that if a person knows the value of the key, they will be able to decrypt the text, and if they do not know, they will not be able to decrypt the text. It's like a real key that can open up access to the message text.



Stage 3/6: Decrypted!
Description
In this stage, you need to support decryption in your program. The decryption is simply the inverse of encryption, following the same steps but reversing the order in which the keys are applied.

Write a program that reads three lines from the standard input: a target operation (enc for encryption, dec for decryption), a message or a cyphertext, and a key to encrypt/decrypt messages. All non-letter characters should be encrypted as well as regular letters. We recommend you to get an integer representation of each character (according to the Unicode table) to shift it.

Decompose your program using methods to make it easy to understand and edit later. One method should encrypt a message and another one should decrypt it according to a key.



Stage 4/6: I command you
Description
Modify the previous program to work with command-line arguments instead of the standard input. The program must parse three arguments: -mode, -key and -data. The first argument should determine the program’s mode (enc for encryption, dec for decryption). The second argument is an integer key to modify the message, and the third argument is a text or ciphertext to encrypt or decrypt.

All the arguments are guaranteed to be passed to the program. If for some reason it turns out to be wrong:

If there is no -mode, the program should work in enc mode.
If there is no -key, the program should consider that key = 0.
If there is no -data, the program should assume that the data is an empty string.
Keep in mind that the order of the arguments might be different. For example, -mode enc may be at the end, at the beginning or in the middle of arguments array.



Stage 5/6: X-files
Description
At this stage, you need to add the ability to read and write original and cipher data to files. The program must parse two additional arguments -in and -out to specify the full name of a file to read data and to write the result. Arguments -mode, -key, and -data should still work as before.

Your program should read data from -data or from a file written in the -in argument. That's why you can't have both -data and -in arguments simultaneously, only one of them.

If there is no -mode, the program should work in enc mode.
If there is no -key, the program should consider that key = 0.
If there is no -data, and there is no -in the program should assume that the data is an empty string.
If there is no -out argument, the program must print data to the standard output.
If there are both -data and -in arguments, your program should prefer -data over -in.
If there is a non-standard situation (an input file does not exist or an argument doesn’t have a value), the program should not fail. Instead, it must display a clear message about the problem and stop successfully. The message should contain the word "Error" in any case.
