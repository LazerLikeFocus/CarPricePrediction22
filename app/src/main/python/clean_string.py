# -*- coding: utf-8 -*-
"""
Created on Fri Oct 30 12:31:15 2020

@author: Abhishek
"""
import numpy
import sklearn
import pickle
from os.path import dirname, join
filename_pos = join(dirname(__file__), "model_LR.pkl")

def find(X):

    #X = X.reshape(1, 9)
    X = numpy.reshape(X, (1, 9))

    # load it agai
    with open(filename_pos, 'rb') as fid:
        gnb_loaded = pickle.load(fid)

    #a = [[90000,1,1,1,1,19,1248,5,9]]
    y_pred = gnb_loaded.predict(X)
    #fig = plt.figure()
    #plt.scatter(y_test,y_pred)


    #from sklearn.metrics import r2_score
    #R2 = r2_score(y_test,y_pred)
    #print(R2)

    #print(y_pred)
    return y_pred[0]


# s = 'Provided to YouTube by Saregama India Ltd Mann Ki Lagan · Rahat Fateh Ali Khan Ek Cup Love℗ 2020 Saregama India LtdReleased on: 1969-09-27'
# print(find(s))

