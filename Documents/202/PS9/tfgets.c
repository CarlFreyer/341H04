#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <stdlib.h>
#include <signal.h>
#include <fcntl.h>
#include <string.h>
#include <errno.h>
                                      
typedef void handler_t;

void unix_error(const char *msg){//unix error
  fprintf(stderr, "%s: %s\n", msg, strerror(errno));
  exit(0);
}

void tfgets_alrm(int sig){
    return NULL;
}

int main(){
  char buffer[500];
  tfgets(buffer, 500, stdin);
  printf("%s", buffer);
}

int Sigemptyset(sigset_t *set){//sigemptyset
  int ret;
  if ((ret = sigemptyset(set)) < 0){
    unix_error("Signal Error");
  }
  return ret;
}

handler_t *Signal(int signum, handler_t *handler){//signal
  struct sigaction action, old_action;
  action.sa_handler = handler;
  Sigemptyset(&action.sa_mask);
  action.sa_flags = SA_RESTART;
  if (Sigaction(signum, &action, &old_action) == SIG_ERR)
  {
    unix_error("Handler error");
  }
  return (old_action.sa_handler);
}
void tfgets(char *s, int size, FILE *stream){
  Signal(SIGALRM, tfgets_alrm);
  alarm(5);
  fgets(s, size, stream);
}