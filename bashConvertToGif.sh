#!/bin/bash
for a in *.png
do
  b=${a%%.gif}
  ./ffmpeg.exe -i "$a" -vf scale=200:200 "$b.gif"
done
rm *.png
