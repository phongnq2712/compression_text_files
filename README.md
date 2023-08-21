# compression_text_files
Author: Quoc Phong Ngo  
Copyright © 2023

An application that will allow users to compress and decompress simple text files. A small menu will provide users with a
handful of options for manipulating and displaying input.

The first option in the menu will be “Display list of files”.
Option 2 – Display file contents. Here, we are simply going to display the content of the input file that we are currently processing.
To display a file, we simply prompt a user for the file name, then read the text content and print it to the screen
Option 3 – Compress a file. In fact, we will be starting with t1.txt, which contains the following text:

The first man is from the last group of people

So let’s select Option 3 and provide the name t1.txt when prompted for a file name. If all goes
well, the compression will be performed, a new compressed file will be created, and the menu will
be re-displayed.

Because the compressed files are text-based, we can again use Option 2 to display the contents. So if we use Option 2 and provide 
the name t1.txt.ct, we should see something like the following displayed:

0 41 374 6 15 0 197 178 1 86

So now we can select Option 4 to uncompress a file. Once we provide the name of a previously compressed file (e.g., t1.txt.ct), 
the program will decompress the file and (hopefully) display the original text directly to the screen.
