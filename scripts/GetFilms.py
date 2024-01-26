import gdown
from checksumdir import dirhash
import shutil
import os

md5remote = "a96fb3a15aa2e9b8e84c6fbe44c5ee2f"
md5hash = dirhash('./filmbox/src/main/resources/static/films', 'md5')
url = "https://drive.google.com/drive/folders/1k5xysbb2GkzPil970SSmCxl8CRJ1ljsD"

if md5hash != md5remote:
    gdown.download_folder(url, quiet=False, use_cookies=False)
    if os.path.isdir('./filmbox/src/main/resources/static/films'):
        shutil.rmtree('./filmbox/src/main/resources/static/films')
    shutil.move('./films', './filmbox/src/main/resources/static')