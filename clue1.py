from Crypto.Cipher import AES
import itertools

# Data
ciphertext_ecb = bytes.fromhex("1f48519a4919974403aca16eff94c0a6")
known_plaintext = b"overcompensation"  # 16 bytes

# Brute-force last 3 digits of K2
for i in range(1000):
    suffix = f"{i:03d}"  # 000 to 999
    key = ("adminpassword" + suffix).encode()
    cipher = AES.new(key, AES.MODE_ECB)
    decrypted = cipher.decrypt(ciphertext_ecb)
    if decrypted == known_plaintext:
        print("Found key:", key)
        print(f"flag1{{adminpassword{suffix}}}")
        break
