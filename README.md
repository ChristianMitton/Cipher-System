# Cipher-System-Assignment
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="content-type" content="text/html; charset=IS-8859-1">

</head><body><center>
<h2>Programming Assignment 1</h2>
<h2>Mini Cipher System</h2>
<h4>In this assignment you will implement a mini cipher system (MCS).
Your implementation will use CIRCULAR linked lists</h4>

<h3>Worth 100 points = 10% of your course grade</h3>
<h3>Posted Mon, Jun 4
</h3><h3>Due Tue, Jun 19, 1:15 AM (<font color="red">WARNING!! NO GRACE PERIOD</font>)
</h3><h3><font color="red">There is NO extended deadline!!</font>
</h3></center>
<hr>

<ul>
<li>You will work <b>individually</b> on this assignment. Read the
<a href="http://www.cs.rutgers.edu/academic-integrity/programming-assignments">
DCS Academic Integrity Policy for Programming Assignments</a> - you are responsible
for this. In particular, note that <b>"All Violations of the Academic
Integrity Policy will be reported by the instructor to the appropriate Dean".</b>


</li><li><h3>IMPORTANT - READ THE FOLLOWING CAREFULLY!!!</h3>

<p><font color="red">Assignments emailed to the instructor or TAs will
be ignored--they will NOT be accepted for grading. <br>
We will only grade submissions in Sakai.</font><br>

</p><p><font color="red">If your program does not compile, you will not get any credit.</font>

</p><p>Most compilation errors occur for two reasons:
</p><ol>
<li> You
are programming outside Eclipse, and you delete the "package" statement at the top of the file.
If you do this, you are changing the program structure, and it will not compile when we
test it.
</li><li> You make some last minute
changes, and submit without compiling.
</li></ol>

<h3>To avoid these issues, (a) START EARLY, and
give yourself plenty of time to work through the assignment, and (b) Submit a version well
before the deadline so there is at least something in Sakai for us to grade. And you can
keep submitting later versions - we will
accept the LATEST version.</h3>
</li></ul>
<hr><p>

</p><ul>
<li><a href="#back">MCS Encryption Algorithm</a>
</li><li><a href="#keystream">Generating the keystream</a>
</li><li><a href="#impl">Implementation</a>
</li><li><a href="#running">Running the Program</a>
</li><li><a href="#submission">Submission</a>
</li><li><a href="#grading">Grading</a>

</li></ul>

<hr>

<p><a name="back"></a></p><h3>MCS Encryption/Decryption Algorithm</h3>

<p>In this assignment, you will implement a mini cipher system that field
agents could use to encrypt their messages securely. You will use 1 to 26 integers as keys, plus
two flag numbers which are 27 and 28. <font color="red"><b>You will store the keys and flag numbers
 in a CIRCULAR linked list</b></font>.
What follows is a description of the encryption/decryption algorithm.

</p><p>The algorithm starts with a sequence in some random order. It uses this
sequence to generate what is called a <em>keystream</em>, which is a sequence of
numbers called <em>keys</em>. Each key will be a number between 1 and 26.
Imagine that you want to encrypt the following message to send to your friend:
</p><pre>   DUDE, WHERE'S MY CAR?
</pre>
Imagine also that you start with the following 26 keys, with the two flags
given values of 27 (Flag "A") and 28 (Flag "B"):
<pre>  INITIAL SEQUENCE:   13 10 19 25 8 12 20 18 26 1 9 22 15 3 17 24 2 21 23 27 7 14 5 4 28 11 16 6
</pre>
Starting with this sequence, you will get the following keystream (you will see how),
one key for every alphabetic character in the message to be encrypted. Here's
the message again, <font color="red">with everything but the letters
taken out of
consideration</font>, followed by a parallel list of integers which correspond
to the positions of the message letters in the alphabet, and finally,
the keystream, one key per character:
<pre>Message:     D   U    D   E   W   H    E    R   E    S    M    Y    C    A   R

Alphabet:    4   21   4   5   23  8    5    18  5    19   13   25   3    1   18
Position

Keystream:   7   16   5   8   8   15   26   9   14   23   12   15   25   3   1
</pre>
<font color="red">Encryption</font> is then done by simply adding each key of the keystream to the
corresponding alphabetic position,
and if this sum is greater than 26, subtracting 26 from the sum. Here's
the resulting sequence of numbers:
<pre>            11   11   9   13  5   23   5    1   19   16   25   14   2    4   19
</pre>
The numbers are converted back to letters, to get the following encrypted message.
<pre>Encrypted:   KKIMEWEASPYNBDS
</pre>

<font color="red">Decryption</font>
follows a similar process.

<p>When the decrypter gets the coded message, she generates
the keystream in exactly the same way,
<font color="red">using the same initial sequence as the encryption</font>.
Then, the keystream is <b>subtracted</b> from the alphabetic position values of the letters
in the coded message. If a code value is equal or smaller
than the corresponding decryption key, 26 is first added to it and then the key is subtracted:
</p><pre>Code:       11   11   9   13   5   23   5   1   19   16   25   14    2   4   19

Keystream:   7   16   5    8   8   15  26   9   14   23   12   15   25   3    1
           --------------------------------------------------------------------
Message:     4   21   4    5   23   8   5  18    5   19   13   25    3   1   18

             D   U    D    E   W    H   E   R    E   S    M    Y     C   A    R
</pre>

<hr>
<h3><a name="keystream"></a>Generating the keystream</h3>

Here is the algorithm to generate each key of the keystream,
starting with the initial sequence.

<p>
<b>Get Key</b>
</p><ul>
<li>Execute the following four steps:
<p></p><ul>
<li><b>Step 1 (Flag A)</b>: Find Flag "A" (27) and move it
ONE position down by swapping it
with the number below (after) it.<br>
This results in the following, after
swapping 27 with 7 in the starting sequence:
<pre>
  INITIAL SEQUENCE:      13 10 19 25 8 12 20 18 26 1 9 22 15 3 17 24 2 21 23 27 7 14 5 4 28 11 16 6
                                                                             ^^^^
  SEQUENCE AFTER STEP 1: 13 10 19 25 8 12 20 18 26 1 9 22 15 3 17 24 2 21 23 7 27 14 5 4 28 11 16 6
                                                                             ^^^^
</pre>
If the flag happens to be the last one in the sequence, then loop around and
swap it with the first. For example:
<pre>  5 ... 27
</pre>
Here 5 is the first number and 27 is the last number. Swapping them will give:
<pre>  27 ... 5
</pre>

<p></p></li><li><b>Step 2 (Flag B)</b>: Find Flag "B" (28) and move it
TWO numbers down by swapping it
with the numbers below (after) it.<br>
This results in the following, after
moving 28 two numbers down in the sequence that resulted after step 1:
<pre>
  SEQUENCE AFTER STEP 1: 13 10 19 25 8 12 20 18 26 1 9 22 15 3 17 24 2 21 23 7 27 14 5 4 28 11 16 6
                                                                                         ^^^^^^^^
  SEQUENCE AFTER STEP 2: 13 10 19 25 8 12 20 18 26 1 9 22 15 3 17 24 2 21 23 7 27 14 5 4 11 16 28 6
                                                                                         ^^^^^^^^
</pre>
If the flag happens to be the last (or second to last)
number in the sequence,  then loop around and
swap it with the number(s) in the front. For example:
<pre>   5 6 ... 10 28
</pre>
Here 28 is the last one. Moving it one position down gives:
<pre>   28 6 ... 10 5
</pre>
and moving it one more position down gives:
<pre>   6 28 ... 10 5
</pre>

<p></p></li><li><b>Step 3 (Triple Shift)</b>: Swap all the numbers before the first (closest to the
top/front) flag with the numbers after the second flag:
<pre>  SEQUENCE AFTER STEP 2: 13 10 19 25 8 12 20 18 26 1 9 22 15 3 17 24 2 21 23 7|27 14 5 4 11 16 28|6
                         ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^                    ^
  SEQUENCE AFTER STEP 3: 6|27 14 5 4 11 16 28|13 10 19 25 8 12 20 18 26 1 9 22 15 3 17 24 2 21 23 7
                         ^                    ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
</pre>
If there are no numbers before the first flag, then the second flag will become the last
one in the modified sequence. Similarly, if there are no numbers after the second flag,
then the first flag will become the first number in the modified sequence.
<p></p></li><li><b>Step 4 (Count Shift)</b>: Look at the value of the last number in the sequence.
Count down that many numbers from the first number, and move those numbers to
just <em>before</em> the last number:
<pre>  SEQUENCE AFTER STEP 3: 6 27 14 5 4 11 16 28 13 10 19 25 8 12 20 18 26 1 9 22 15 3 17 24 2 21 23 7

  SEQUENCE AFTER STEP 4: 28 13 10 19 25 8 12 20 18 26 1 9 22 15 3 17 24 2 21 23 6 27 14 5 4 11 16 7
                                                                                ^^^^^^^^^^^^^^^^^
</pre>
If the last number happens to be Flag B (28), use 27 (instead of 28) as its value
for this step.
</li></ul>
<p></p></li><li>After these four steps are done, look at the value of the first number. If it is 28,
then treat the value as 27. Count down by
that many numbers from the first. Look at the value of the <b>next</b> number.
If it happens NOT to be 27 or 28, this is the key. <font color="red">Otherwise,
repeat the whole process (Flag A through Count Shift)
with the latest (current) sequence (NOT the initial sequence).</font>
<pre>  SEQUENCE AFTER STEP 4: 28 13 10 19 25 8 12 20 18 26 1 9 22 15 3 17 24 2 21 23 6 27 14 5 4 11 16 7

  SEQUENCE AFTER STEP 5: 28 13 10 19 25 8 12 20 18 26 1 9 22 15 3 17 24 2 21 23 6 27 14 5 4 11 16 7
</pre>
In this example, the first number is 28, so treat it as 27.
The 27th number in the sequence is 16, and the next number is 7, so 7 is the key.
</li></ul>
Once a key is found, the algorithm is repeated to find the subsequent keys,
starting every time with the current sequence (NOT the initial sequence).

<hr>

<p><a name="impl"></a></p><h3>Implementation</h3>

Download the <tt>miniCipherSys_project.zip</tt> file attached in the Sakai assignment.
DO NOT unzip it.
Instead, follow the instructions on the Eclipse page under the section "Importing a Zipped
Project into Eclipse" to get the entire project into your Eclipse workspace.

<p>You will see a project called <tt>MiniCipherSys</tt> with the classes
<tt>SeqNode</tt> (which implements a node of the linked list sequence),
<tt>MiniCipherSys</tt> (which implements the encryption and decryption algorithms), and
<tt>Messenger</tt> (the application driver), all in the package <tt>mcs</tt>.

</p><p>You will also see a sample input file, <tt>sequence.txt</tt>, DIRECTLY UNDER THE
PROJECT FOLDER (NOT under mcs or src). This is where other input files must go when you
test your program.

</p><p>You need to fill in the implementation of the <tt>MiniCipherSys</tt> class where
indicated in the <tt>MiniCipherSys.java</tt> source file. This includes the
following:

<table>
<tbody><tr><th>Method</th><th>Points</th></tr>
<tr><td><tt>flagA</tt></td><td class="pts">13</td></tr>
<tr><td><tt>flagB</tt></td><td class="pts">13</td></tr>
<tr><td><tt>tripleShift</tt></td><td class="pts">27</td></tr>
<tr><td><tt>countShift</tt></td><td class="pts">21</td></tr>
<tr><td><tt>getKey</tt></td><td class="pts">8</td></tr>
<tr><td><tt>encrypt</tt></td><td class="pts">10</td></tr><tr>
</tr><tr><td><tt>decrypt</tt></td><td class="pts">8</td></tr>
</tbody></table>

</p><p>The <tt>printList</tt> method has been implemented for your convenience - you
may use it to print the sequence for verification/debugging. (But it is NOT used
by us when testing your program - see the Grading section at the
end for details.)

</p><p>Do NOT change <tt>SeqNode.java</tt> in any way.

</p><p>While working on <b>MiniCipherSys.java</b>:
</p><ul>
<li>You may NOT add any <tt>import</tt> statements to the file.
<p>Note: Sometimes Eclipse will automatically add import statements to the file,
if you are using an unknown class other than the ones you are given.
<font color="red">It is your responsibility to delete
such automatically added import statements. At the time of grading, we will
delete all such additional import statements we find, before compiling your code.
If your code does not compile, we will not be able to test it, and you will get
a zero.</font>
</p></li><li>You MUST work with CIRCULAR LINKED LISTS only. You may NOT transfer the contents of
the linked lists to arrays or other data structures for processing.
</li><li>You may NOT add any new classes (you will only be submitting <tt>MiniCipherSys.java</tt>).
</li><li>You may NOT add any fields to the <tt>MiniCipherSys</tt> class.
</li><li>You may NOT modify the headers of any of the given methods.
</li><li>You may NOT delete any methods.
</li><li>You MAY add helper methods if needed, as long as you make them <tt>private</tt>.
</li></ul>

<p></p><h4>Notes on character manipulation</h4>

The <tt>java.lang.Character</tt> class has several useful methods for manipulating characters.
Here are a few you may find useful.
<ul>
<li><tt>Character.toUpperCase(ch)</tt>
</li><li><tt>Character.toLowerCase(ch)</tt>
</li><li><tt>Character.isLetter(ch)</tt>
</li></ul>

<p>You can switch between character and integer values using casts.
Here's an example that starts with the character 'D', gets its
position in the alphabet (4), adds 1 to it, and gets the next
character ('E'):
</p><pre>    char ch = 'D';
    System.out.println(ch);  // D
    int c = ch-'A'+1
    System.out.println(c);   // 4
    c++;
    ch = (char)(c-1+'A');
    System.out.println(ch);  // E
</pre>

<p><font color="red" size="+1">Make sure you read the comments above each method
in the code for additional details on what the method does.
This information, in addition to the specification in this document,
is essential to correctly implement the method.</font>

</p><hr>

<p><a name="running"></a></p><h3>Running the Program</h3>

<p>The class <tt>Messenger</tt> has a <tt>main</tt> method, so it can
be run as a Java application.

</p><p>Here's a sample run to encrypt a message, with the sequence supplied in the
file <tt>sequence.txt</tt> (which came with the project):
</p>
<pre>  Enter sequence file name =&gt; sequence.txt
  Encrypt or decrypt? (e/d), press return to quit =&gt; e
  Enter message =&gt; DUDE, WHERE'S MY CAR?
  Encrypted message: ODALGGPCLAVJNAP
</pre>
And here's a sample to decrypt a message that was encrypted using <tt>sequence.txt</tt>:
<pre>  Enter sequence file name =&gt; sequence.txt
  Encrypt or decrypt? (e/d), press return to quit =&gt; d
  Enter message =&gt; ODALGGPCLAVJNAP
  Decrypted message: DUDEWHERESMYCAR
</pre>

<p>You should look at the code in <tt>Messenger</tt> and understand that it
reads the sequence from whatever input file you specify, and sets up
the MiniCipherSys object's linked list for this sequence, by calling the
<tt>makeSeq</tt> method. It then calls the <tt>encrypt</tt> or
<tt>decrypt</tt> methods.

</p><p>Make sure you test your code comprehensively with other test cases of your own.
Pay particular attention to the extreme or special cases in each of the steps of
the algorithm. Remember, when you create other test input files, you should put
them alongside <tt>sequence.txt</tt>, DIRECTLY UNDER THE PROJECT FOLDER.

</p><p>You may assume that all input sequences will be legitimate: a sequence
will consist of (only) values from 1 through 28 in some order. So you
don't need to do any check on input format.

</p><p>You may also assume that all the letters in the string to be encrypted or
decrypted will be uppercase, so you don't need to do any checking.
But the string to be encrypted may have
spaces, punctuation characters, etc. which should be ignored while encrypting.
So the decrypted string will only have uppercase letters, even if the
input string that was encrypted had characters other than letters.

</p><p>Since there will no be no errors in input, you will not need to throw
any exceptions.

</p><hr>

<p><a name="submission"></a></p><h3>Submission</h3>

<p>Submit your <b>MiniCipherSys.java</b> source file (NOT MiniCipherSys.class) in Sakai.<br>

</p><p>You will ONLY submit this file, which means you should not make any changes
to <tt>SeqNode.java</tt> because we will be using the original <tt>SeqNode.java</tt>
to test your implementation.

</p><hr>

<p><a name="grading"></a></p><h3>Grading - IMPORTANT!!!</h3>

<p>Your submission will be auto-graded by a grading script that will
run several test cases on each graded method. For each test case, the result
computed by your code will be compared with that computed by our
correct code.

</p><p>Result computed by your code means this:
</p><ul>
<li>The actual configuration of
the circular linked list in your MiniCipherSys object (pointed to by <tt>seqRear</tt>)
computed by the methods <tt>flagA</tt>, <tt>flagB</tt>, <tt>tripleShift</tt>,
and <tt>countShift</tt>
</li><li>The integer returned by <tt>getKey</tt>
</li><li>The string returned by each of <tt>encrypt</tt> and <tt>decrypt</tt>.
</li></ul>
<p>Result does NOT refer to anything your program might print as "output".
<font color="red">All printed output will be
ignored</font>, including any debugging statements you might have
left lying around in your code - they will have no bearing on the
tests we run.

</p><p>When grading is done, your test report will be emailed, detailing the
score on each test case. Some of the test cases will be posted so you can run your
program against them to verify the test report.


</p><p>Your methods will be tested <em>independently</em>. This means
the grade for a method is independent of the grade for any
other method. So, for instance, when we grade <tt>countShift</tt>,
we will <em>not</em> use your <tt>flagA</tt>,
<tt>flagB</tt>, and <tt>tripleShift</tt> implementations for the calls
that precede the call to <tt>countShift</tt>.
Instead, we will use our correct implementations of these
methods. This way, even if any of these other methods does not work
correctly, you will still get credit for <tt>countShift</tt> if it
works correctly by itself.

</p>
</body></html>
