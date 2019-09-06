# Theory of Computation - INE-UFSC
Class Activities. Theory of Computation, UFSC, INE. 2019

Professor: Maicon Rafael Zatelli

Activity 01: https://www.urionlinejudge.com.br/judge/pt/problems/view/1231

# Words - URI Online Judge | 1231

Maratona de Programação da SBC  Brazil

Timelimit: 1
Given two sets of words formed by zeros and ones, you must write a program to determine if there are concatenations of words of each of the sets that generate the same word. For example, if a set A contains the words 010 and 11 and another set B contains the words 0 and 101, then the word 01011010 can be formed both by concatenating words of A and by concatenating words of B.


010 · 11 · 010 = 01011010 = 0 · 101 · 101 · 0 

### Input
The input contains several test cases. The first line of a test case contains two integers, N1 (1 ≤ N1), and N2 (N2 ≤ 20), which indicate respectively the number of words in the first and the number of words in the second sets. Each of the following N1 lines contains a word of the first set. Each of the following N2 lines contains a word of the second set.

Note: Each word has at least one and at most 40 characters, all zeros and ones.

### Output
For each test case your program must print a single line, containing a single character. If it is possible to find a concatenation of one or more words of the first set that is equal to a concatenation of one or more words of the second set then the character must be S, otherwise the character must be N.