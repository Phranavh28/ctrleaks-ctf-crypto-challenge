from Crypto.Cipher import AES
from Crypto.Util.strxor import strxor

# Use only the first 16 bytes (AES block size)
plaintext_cbc = b"photorealistically"[:16]
ciphertext_cbc = bytes.fromhex("12a6ca55c182bcac8f91dcbcfd633e17")

# Key from Flag 1
key2 = b"adminpassword815"

# Decrypt the first ciphertext block using AES-ECB
cipher = AES.new(key2, AES.MODE_ECB)
decrypted_block = cipher.decrypt(ciphertext_cbc)

# XOR decrypted block with known plaintext to get IV
iv = strxor(decrypted_block, plaintext_cbc)

# Print the results
print("IV (raw bytes):", iv)
try:
    print("IV as ASCII (Flag 2):", f"flag2{{{iv.decode('ascii')}}}")
except UnicodeDecodeError:
    print("Could not decode IV. Try printing it in hex.")
