# baidu_image_search.py
from DrissionPage import ChromiumPage
import time
import sys


def baidu_image_search_by_url(image_url: str):
    page = ChromiumPage()

    # 1. 打开百度图片搜索页面
    page.get('https://graph.baidu.com/pcpage/index?tpl_from=pc')
    time.sleep(1)

    # 2. 定位 input 并设置值
    input_ele = page.ele('xpath://div[@class="page-search"]/div[1]/span[1]/input[1]')
    input_ele.clear()
    input_ele.input(image_url)

    # 3. 点击搜索按钮触发搜索
    btn = page.ele('xpath://div[@class="page-search"]/div[1]/span[2]')
    btn.click()
    time.sleep(3)

    # 4. 获取当前页面网址
    latest_tab = page.get_tab(page.latest_tab)

    return latest_tab.url


if __name__ == '__main__':
    try:
        image_url = sys.argv[1]
        result_url = baidu_image_search_by_url(image_url)
        # 直接输出 URL，Java 读取 output 即为 URL
        print(result_url)
    except Exception as e:
        print(f"ERROR: {e}")
        sys.exit(1)