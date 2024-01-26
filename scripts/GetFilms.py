import gdown
from checksumdir import dirhash
import shutil
import os

staticpath = './filmbox/src/main/resources/static'
md5remote = "a96fb3a15aa2e9b8e84c6fbe44c5ee2f"
md5hash = dirhash(staticpath + '/films', 'md5')
url = "https://drive.google.com/drive/folders/1k5xysbb2GkzPil970SSmCxl8CRJ1ljsD"

if md5hash != md5remote:
    gdown.download_folder(url, quiet=False, use_cookies=False)
    if os.path.isdir(staticpath + '/films'):
        shutil.rmtree(staticpath + '/films')
    shutil.move('./films', staticpath)