Avery Nelson 
Last Updated: 7/7/2023

# achprocessing
Merges bank file with R&T and account numbers with valid checks

For this script, the banking.txt should contain the company name, the R&T number, account number, blank columns for check ID and amount, and an account type (which should be a check). Vendors should be loaded in this file. Importing.txt contains the dates, check numbers, payee, and amount owed. The list of checks being paid out should be in this file. Both files should be tab delimited. After these files are saved and ready, the build script included should be run. A dated export file and a dated errors file are produced.