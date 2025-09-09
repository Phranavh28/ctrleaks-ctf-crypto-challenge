#  CTRLeaks CTF Challenge – Complete Decryption Attempt Summary

This repository documents all the work done to solve the **CTRLeaks cryptographic challenge**, which involved decrypting three AES-encrypted messages using ECB, CBC, and CTR modes. The challenge required advanced brute-force strategies, programming across multiple languages, and deep understanding of block cipher mechanics.

The work is divided into clearly structured phases for each flag. Every attempt, success, and failure is documented with scripts, logs, and tool-based screenshots. You can use the provided code to replicate, analyze, or build upon the methodology.

---

##  Challenge Overview

### Scenario
The scenario revolves around an intercepted message believed to contain three English words, encrypted using AES in CTR mode. Additionally, supporting messages were encrypted using AES in ECB and CBC modes, allowing attackers to exploit known weaknesses such as reused IVs and weak key derivation.

### Goals

- **Flag 1:** Recover the AES key K2 used in AES-ECB.
- **Flag 2:** Recover the reused IV used in both CBC and CTR modes.
- **Flag 3:** Decrypt the final message encrypted using AES-CTR.

---

##  Summary of Attempts

###  Flag 1 – AES-ECB Brute Force

- Known plaintext: `overcompensation`
- Ciphertext (Hex): `1f48519a4919974403aca16eff94c0a6`
- Key Format: `adminpassword000` to `adminpassword999` (1,000 keys)
- Tools used:
  - CyberChef & Cryptii (manual, failed)
  - R (success)
  - Python (validation)
- Final key found: `adminpassword815`
- Flag submitted: `flag1{adminpassword815}` 

---

###  Flag 2 – AES-CBC IV Extraction

- Known plaintext: `photorealistically`
- Ciphertext: AES-CBC encrypted with same key as Flag 1
- Key used: `adminpassword815`
- Tools used:
  - R (primary)
  - Python (validation)
- Decryption logic:
  - IV = Decrypt(C1) XOR P1
  - Used first 16-byte ciphertext block
- Final IV recovered: `unimaginableness`
- Flag submitted: `flag2{unimaginableness}` 

---

###  Flag 3 – AES-CTR Brute Force

- Ciphertext: 2-block AES-CTR encrypted message
- Keyspace derived from digits of student number: {0,1,3,4,7,9}
- Estimated keyspace: 6^16 ≈ 2.8 trillion combinations
- IV used: `unimaginableness` (from Flag 2)
- Format: `flag{word1_word2_word3}`

#### Attempts:
1. **Python brute-force with PyCryptodome**
   - Tested ~10 million keys over 3 days on Mac
   - Early-rejection logic based on ASCII/spellcheck
   - Result: No valid plaintext

2. **R permutation-based decryption**
   - Used digit patterns from student number
   - Constructed simulated CTR cipher using manual counter logic
   - Decrypted ~1,000 keys
   - Result: No match

3. **Java multithreaded AES-CTR using javax.crypto**
   - Split digit-prefix keyspace across threads
   - Basic regex pattern-matching for English words
   - Result: ~2.5 million keys tested, no valid output

4. **CyberChef / Cryptii**
   - No automation or key iteration possible
   - Inadequate for AES-CTR brute-force
   - Result: Manual attempts failed

5. **Permutation generator with repeated sequences**
   - Used patterns like `4073190407319040`
   - Tested for block-aligned English phrases
   - Result: Not found

- Placeholder flag submitted: `flag3{secure_hidden_bunker}` 

---

## 🗂 Files Included

| File Name              | Description |
|------------------------|-------------|
| `clue1.py`             | Python brute-force for Flag 1 |
| `clue2.py`             | CBC IV extraction logic |
| `clue3.py`             | Flag 3 decryption script (CTR mode) |
| `Clue-3.1.py`          | Optimized CTR brute-force in Python |
| `Flag3_Java.java`      | Java multithreaded AES-CTR key testing |
| `ctrleaks_solution.Rmd`| R code for Flag 1 + 2 decryption |
| `README.md`            | This file |

---

##  Key Learnings

- AES-ECB and AES-CBC are vulnerable to brute-force and structural attacks if poor key or IV practices are followed.
- AES-CTR remains secure if entropy is high and IVs are non-repeating.
- Key derivation and IV reuse errors can be exploited with XOR logic.
- Brute-force without narrowing the keyspace (≈2.8 trillion) is computationally infeasible within reasonable time.
- Languages like R, Python, and Java offer varied performance vs. memory vs. flexibility trade-offs in crypto tasks.

---

## ⚙️ How to Use

### 1. R Environment (Flag 1 and 2)
- Open `ctrleaks_solution.Rmd` in RStudio.
- Install dependencies: `openssl` or `sodium` package.
- Execute chunks in order to validate AES logic.

### 2. Python Scripts
- Requires Python 3.x.
- Install packages: `pip install pycryptodome`.
- Run:
  ```bash
  python clue3.py
  python clue2.py
  ```

### 3. Java Code
- Requires JDK 8+.
- Compile and run:
  ```bash
  javac Flag3_Java.java
  java Flag3_Java
  ```

---

## 📎 Source Folder / Drive Link

All code, screenshots, brute-force logs, and reports are included in the shared drive folder:

➡️ **[https://drive.google.com/drive/folders/1eK2uQxj5wQoUIWRqOS695KVmC8ybR1Rl?usp=share_link]**

Open access is provided for:
- Peer review
- Instructor assessment
- Cryptography enthusiasts wishing to explore the codebase

---

##  Final Notes

All code used in the project has been developed independently unless provided as templates during coursework. Attempts have been documented in a structured way to promote reproducibility and transparency. Suggestions and improvements for optimizing AES key recovery techniques are welcome.

---

Created for academic purposes – CTRLeaks Decryption Project – 2025
