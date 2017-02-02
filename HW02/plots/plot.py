import numpy as np
import matplotlib.pyplot as plt
import os
import glob
import re
from os.path import basename, join, dirname

def extract_point(line):
    if not line: return
    pt_data = re.match( '^{(.*)}; f.*$', line).group(1)
    x, y = pt_data.split(';')
    return float(x), float(y)


def plot_file(filepath):
    dirpath = dirname(filepath)
    name = basename(filepath).split('.')[0]

    lines=open(filepath, 'r').readlines()
    data = filter(None, [extract_point(l.strip()) for l in lines])
    X, Y = zip(*data)

    plt.figure()
    plt.plot(X, Y)
    plt.title(name)
    plt.xlabel('x1'); plt.ylabel('x2')
    plt.savefig(join(dirpath, name+'.jpg'))


root_dir = './'
filepaths = glob.glob(join(root_dir, '*.txt'))
for filepath in filepaths:
    plot_file(filepath)
