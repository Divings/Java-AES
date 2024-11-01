import tkinter as tk
import subprocess

# バッチファイルを新しいコンソールウィンドウで実行する関数
def run_encrypt():
    subprocess.Popen("start cmd /c java -jar Encrypt.jar", shell=True)

def run_decrypt():
    subprocess.Popen("start cmd /c java -jar Decrypt.jar", shell=True)

# GUIウィンドウの設定
window = tk.Tk()
window.title("Encrypt/Decrypt")
window.geometry("300x200")

try:
    # アイコンの設定 (アイコンファイルのパスを指定)
    window.iconbitmap("path/to/your/icon.ico")
except:
    pass

# Encryptの説明ラベル
encrypt_label = tk.Label(window, text="ファイルを暗号化するには以下のボタンを押してください。")
encrypt_label.pack(pady=5)

# Encryptボタン
encrypt_button = tk.Button(window, text="Encrypt", command=run_encrypt)
encrypt_button.pack(pady=10)

# Decryptの説明ラベル
decrypt_label = tk.Label(window, text="暗号化されたファイルを復号化するには\n以下のボタンを押してください。")
decrypt_label.pack(pady=5)

# Decryptボタン
decrypt_button = tk.Button(window, text="Decrypt", command=run_decrypt)
decrypt_button.pack(pady=10)

# ウィンドウを表示
window.mainloop()
