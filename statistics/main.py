import pymysql
import csv
import pandas as pd

# 获取 mysql 数据
con = pymysql.connect(
    host='127.0.0.1',
    port=3306,
    user='root',
    password='1356',
    db='stockmonitorsystem',
    charset='utf8'
)
cur = con.cursor()
select = cur.execute("select * from stock_change")
allSc = cur.fetchall()
mx = len(allSc)

# 数据处理
# 用第一个子元组初始化聚合列表
mtList = list(allSc[0])
# 遍历之后的子元组
for index in range(1, mx):
    # 提取子元组中的字段数据，易于理解
    aid = allSc[index][0]
    adt = allSc[index][1]
    ahp = allSc[index][2]
    alp = allSc[index][3]
    aop = allSc[index][4]
    acp = allSc[index][5]
    avl = allSc[index][6]
    if isinstance(mtList[0], int):
        mid = mtList[0]
        mdt = mtList[1]
        mhp = mtList[2]
        mlp = mtList[3]
        mop = mtList[4]
        mcp = mtList[5]
        mvl = mtList[6]
        # 当 stock_id 和年月份相同时进行聚合
        if aid == mid and mdt.year == adt.year and mdt.month == adt.month:
            # 聚合最高低价时取极值
            if mhp < ahp:
                mtList[2] = ahp
            if mlp > alp:
                mtList[3] = alp
            # 定义最早日期与最晚日期以方便之后开盘价与收盘价的聚合
            eld = mdt.day
            ltd = mdt.day
            # 聚合开盘价和收盘价时以日期最早为开盘，日期最晚为收盘
            if eld > adt.day:
                mtList[4] = aop
                eld = adt.day
            if ltd < adt.day:
                mtList[5] = acp
                ltd = adt.day
            # 聚合交易量时直接相加
            mtList[6] += avl
            continue
        else:
            # 如果不符合聚合条件就在列表中添加新列表
            tempList = list(allSc[index])
            mtList = [mtList, tempList]
            continue
    else:
        # 定义一个信号值来判断待处理数据在聚合数据中能否找到对应月份
        signal = 0
        for item in mtList:
            # 将聚合列表中的值提取出来，易于理解
            mid = item[0]
            mdt = item[1]
            mhp = item[2]
            mlp = item[3]
            mop = item[4]
            mcp = item[5]
            mvl = item[6]
            # 当 stock_id 和年月份相同时进行聚合
            if aid == mid and mdt.year == adt.year and mdt.month == adt.month:
                # 聚合最高低价时取极值
                if mhp < ahp:
                    item[2] = ahp
                if mlp > alp:
                    item[3] = alp
                # 定义最早日期与最晚日期以方便之后开盘价与收盘价的聚合
                eld = mdt.day
                ltd = mdt.day
                # 聚合开盘价和收盘价时以日期最早为开盘，日期最晚为收盘
                if eld > adt.day:
                    item[4] = aop
                    eld = adt.day
                if ltd < adt.day:
                    item[5] = acp
                    ltd = adt.day
                # 聚合交易量时直接相加
                item[6] += avl
                break
            else:
                # 每次找不到对应月份信号值自增1
                signal += 1
                continue
        # 最后如果信号值与聚合列表中个数相等，这说明待处理数据在聚合列表中未找到对应月份
        # 所以将待处理数据加入到聚合列表中
        if signal == len(mtList):
            tempList = list(allSc[index])
            mtList.append(tempList[:])
# 重新配置列表的时间信息
for item in mtList:
    mYear = item[1].year
    mMonth = item[1].month
    del item[1]
    item.insert(1, mYear)
    item.insert(2, mMonth)
# 定义输出表格的列名称
column = ['stock_id', 'year', 'month', 'price_high', 'price_low', 'price_open', 'price_close', 'volume']
# 将列名与数据放入表格
monthForm = pd.DataFrame(columns=column,data=mtList)
# 将表格数据存入 csv 文件
monthForm.to_csv('monthForm.csv',index=False)
print("finished successfully!")
