#!/usr/bin/env python
import numpy as np
import sys
import argparse
from argparse import ArgumentParser
import os
		
class Node(object):
	def __init__(self, id, content, prev, next):
		self.id = id
		self.content = content
		self.prev = prev
		self.next = next

def hardGraph():
	graph = {
		'A': ['B', 'C'],
		'B': ['C', 'D'],
    	'C': ['D'],
        'D': ['C'],
        'E': ['F'],
        'F': ['C']
    }

def distance_to(a,b):
	""" 
	a and be should be points with x y values
	"""
	# sqrt ( dx^2 + dy^2)
	return np.sqrt( (a[0]-b[0])**2 + (a[1]-b[1])**2)

def extant_file(x):
	"""
	'Type' for argparse -checks that the file exists
	"""
	if not os.path.exists(x):
		raise argparse.ArgumentError("{0} does not exist".format(x))
	return x

def get_nearest(location, nodes):
	distance = -1
	nearest = []
	for node in nodes:
		if distance < 0:
			distance = distance_to(location, node)
		elif distance > distance_to(location, node):
			nearest = node
	return nearest

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
		l1 = l1.split()
		l1 = [np.float(l1[0]), np.float(l1[1])]
		data = np.loadtxt(f) # Grab the data
	
	print data
	print l1
	
	## preform a basic sort, by soonest deadline.
	data = data[data[:,2].argsort()]
	distance = 0
	nodes = []
	#for row in range(len(data)):
		#nodes.append(DeliveryLocation(data[row,0],data[row,1],data[row,2]))
	#	nodes.append(data[row,:])
	## Set up CSP
	#l1 = l1.split()
	
	location = l1
	distance = 0

	print data
	done = True
	path = []
	path.append(l1)
	for node in data:
		distance = distance + distance_to(location, node)
		path.append(node)
		if distance > node[2]:
			print "failed at node" + str(node)
			done = False
			break
		location = node

	if not done:
		print path.pop()

		print "Must do something smart"



	#for node in nodes:



	## Search for Solution to CSP

if __name__ == '__main__':
	main(sys.argv)