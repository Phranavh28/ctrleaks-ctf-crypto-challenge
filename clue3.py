from Crypto.Cipher import AES
from itertools import product

# Only digits allowed
digits = ['0', '1', '3', '4', '7', '9']
iv = b'unimaginableness'
ciphertext = bytes.fromhex("20c215a87443928d16e1993373955f2128a7633fabbe1874cb1f73cbd2d71306e11485486e1980f48e39b215e5ae7a66")

def is_english(plaintext):
    try:
        text = plaintext.decode('ascii')
        words = text.strip().split()
        return len(words) == 3 and all(w.islower() and w.isalpha() for w in words)
    except:
        return False

counter = 0
for key_tuple in product(digits, repeat=16):
    key = bytes([int(x) for x in key_tuple])
    cipher = AES.new(key, AES.MODE_CTR, nonce=b'', initial_value=iv)
    decrypted = cipher.decrypt(ciphertext)
    if is_english(decrypted):
        print("FOUND:", decrypted.decode())
        break
    counter += 1
    if counter % 100000 == 0:
        print(f"Tried {counter} keys")
