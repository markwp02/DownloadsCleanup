# DownloadsCleanup
 Apache Camel Springboot application to be able to organise the files within the downloads directory

To point Downloads cleanup to your downloads directory, update the property within application.properties

```
source.folder
```
to point to your downloads directory eg. C:\{user}\Downloads

also update the property
```
base.destination.folder
```
where you want the output to go.

Downloads cleanup will route all configured types in the following format

{baseFolder}\{type}\{MM MONTH}

eg. C:\DownloadsCleanup\text\10 October\

Adding the archive date gives a record of all files of a particular type that were routed,
reducing the list of files that potentially needs to be dealt with