## JCipher
JCipher is a cryptographic tool that implements algorithms for encryption, decryption and key generation. The program is based on symmetric as well as asymmetric algorithms. The tool is still being developed, gradually adding new algorithms.

Ultimately, the program is intended to be a CLI, but due to the constant addition of algorithms, the main class file is being rewritten to test cryptographic solutions faster.
### Currently existing algorithms
`Cezar` `DES` `Rot13` `RSA` `Vernam`
### Options
| Flag | Long Form    | Description                                        | Example                           |
|------|--------------|----------------------------------------------------|-----------------------------------|
| `-t` | `--type`     | Select algorithm type. (RSA, DES, VERNAM...)       | `--type "RSA"`                    |
| `-e` | `--encrypt`  | Select file for encryption.                        | `--encrypt fileForEncryption.txt` |
| `-d` | `--decrypt`  | Select file for decryption.                        | `--decrypt fileForDecryption.txt` |
| `-g` | `--generate` | Generate key / keys for provided algorithm.        | `--generate` / `--generate 2048`  |
| `-k` | `--key`      | Select file with key for encryption or decryption. | `--key keyFile.txt`               |
| `-o` | `--output`   | Writes output to file.                             | `--output output.txt`             |
| `-h` | `--help`     | Prints program instruction to the console.         | `--help`                          |
### Notes
* "2048" in `--generate` options example refers to number of **bits** for the key. Number of bits are only applicable for Vernam and RSA.
### Attention
Please take into account that a significant part of the algorithms is not recognized as a cryptographic standard due to their low security level. The program also includes, classical ciphers, which are treated more as a curiosity than viable solutions.
### Installation
After improving the main class *(src/JCipher)* so that it reads **String[] args** instead of **tempArgs** array, the compiled program can be added as an alias to *.bashrc* on Linux for global access.
```Bash
alias jcipher='java /program/location/JCipher'
```