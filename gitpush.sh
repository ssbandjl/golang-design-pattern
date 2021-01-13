#!/bin/bash

[ $# -gt 0 ] && {
  commitInfo=$1
}||{
  commitInfo='update, ref:https://github.com/ssbandjl/golang-design-pattern'
}

git pull
git add .
git commit -m "$commitInfo"
git push
git log|head -n 20
