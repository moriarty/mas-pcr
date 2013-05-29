#!/usr/bin/env python
import numpy as np
import sys
import argparse
from argparse import ArgumentParser
import os
		
def extant_file(x):
	"""
	'Type' for argparse -checks that the file exists
	"""
	if not os.path.exists(x):
		raise argparse.ArgumentError("{0} does not exist".format(x))
	return x

def main(argv):
	## Parse the options

	parser = ArgumentParser()
	#parser.add_argument("-h", "--help")
	parser.add_argument("-i", "--input",
		dest="filename", required=True, type=extant_file,
		help="input file", metavar="FILE")

	args = parser.parse_args()

	## Load Data
	with open(args.filename,'r') as f:
		l1 = f.readline() # Grab the first line
		l1 = l1.split() # readline returned a string
		l1 = [np.float(l1[0]), np.float(l1[1])] # array from l1
		data = np.loadtxt(f) # Grab the data from rest of file

if __name__ == '__main__':
	main(sys.argv)