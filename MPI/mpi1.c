#include <stdio.h>
#include <mpi.h>
/* Exemple du cours */

int main(int argc, char *argv[]) {
  int num_tasks, rank, namelen;
  char processor_name[MPI_MAX_PROCESSOR_NAME];

  MPI_Init(&argc, &argv);
  MPI_Comm_size(MPI_COMM_WORLD, &num_tasks);
  MPI_Comm_rank(MPI_COMM_WORLD, &rank);
  MPI_Get_processor_name(processor_name, &namelen);

  printf("Tâche %d sur le noeud %s parmi %d tâches\n", rank, processor_name, num_tasks);

  MPI_Finalize();
}
