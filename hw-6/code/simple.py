#!/usr/bin/env python
import argparse
parser = argparse.ArgumentParser()
parser.add_argument("-i", "--infile", help="The input file")
args = parser.parse_args()
if args.verbose:
        print "verbosity turned on"
