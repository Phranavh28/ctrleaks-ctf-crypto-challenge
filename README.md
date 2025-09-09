# 🔐 CTRLeaks CTF – AES Cryptography Challenge

This repository contains my write-up and full code for the **CTRLeaks Capture The Flag (CTF)** challenge. The task involved recovering encrypted flags using AES in **ECB**, **CBC**, and **CTR** modes — each requiring a different cryptanalysis technique.

---

## 📌 Challenge Overview

The challenge consisted of 3 stages:
1. **AES-ECB**: Brute-force using known-plaintext to recover the key and decrypt the flag.
2. **AES-CBC**: XOR-based recovery of Initialization Vector (IV) with partial known-plaintext.
3. **AES-CTR**: Attempted brute-force key recovery over a large keyspace (~2.8T keys) using Python, R, and Java.

---

## 🚩 Results

| Challenge | Technique Used                         | Outcome                     |
|-----------|----------------------------------------|-----------------------------|
| ECB       | Known-plaintext brute-force (Python/R) | ✅ `flag1{adminpassword815}` |
| CBC       | IV extraction via XOR (R)              | ✅ `flag2{unimaginableness}` |
| CTR       | CTR key brute-force (Python/Java)      | ⚠️ Infeasible (2.8T keyspace) |

---

## 📂 Repository Structure

ctrleaks-ctf-crypto-challenge/
│
├── docs/
│   └── CTRLeaks_CTF_Challenge_Phranavh.pdf   # Full write-up
│
├── python/
│   ├── clue1.py          # AES-ECB brute-force
│   ├── clue2.py          # CBC IV extraction
│   ├── clue3.py          # Basic CTR brute-force
│   └── Clue-3.1.py       # Optimized CTR brute-force
│
├── r/
│   └── ctrleaks_solution.Rmd   # R Markdown for ECB & CBC analysis
│
├── java/
│   └── Flag3_Java.java   # Multithreaded AES-CTR brute-force
│
├── README.md             # Project overview

## 🧰 Tools & Languages

- **Python** (PyCryptodome)
- **R** (sodium, openssl)
- **Java** (javax.crypto)
- **CyberChef** (for visual analysis and validation)

---

## 💡 Key Takeaways

- Demonstrated AES cryptanalysis using real-world CTF-style data
- Highlighted limitations of brute-force in CTR mode with large keyspaces
- Showcased scripting in Python, R, and Java for cybersecurity problem-solving

---

## 🧑‍💻 Author

**Phranavh Sivaraman**  
Master of Cyber Security – Blue Team | Threat Detection | OSINT | SOC  
[LinkedIn](https://www.linkedin.com/in/phranavhsivaraman/) 
