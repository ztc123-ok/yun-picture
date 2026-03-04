import sys
import os
from DrissionPage import SessionPage


def download_image(img_url, file_path):
    save_dir = os.path.dirname(file_path)
    if save_dir and not os.path.exists(save_dir):
        os.makedirs(save_dir)

    page = SessionPage()
    page.set.headers({
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36'
    })

    print("执行python脚本下载：",img_url)
    page.get(img_url)

    if page.response.status_code == 200:
        with open(file_path, 'wb') as f:
            f.write(page.response.content)
        print("SUCCESS")
    else:
        print("FAIL")
        sys.exit(1)


if __name__ == '__main__':
    print(sys.argv[1])
    print(sys.argv[2])
    try:
        download_image(sys.argv[1], sys.argv[2])
    except Exception as e:
        print("FAIL")
        sys.exit(1)