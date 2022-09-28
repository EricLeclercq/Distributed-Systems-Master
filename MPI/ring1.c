#include <stdio.h>
#include <mpi.h>


int main(int argc, char *argv[]) {
  int num_tasks, my_rank, namelen;
  char processor_name[MPI_MAX_PROCESSOR_NAME];
  int message=1;
  int n=0;
  MPI_Status status;

  MPI_Init(&argc, &argv);
  MPI_Comm_size(MPI_COMM_WORLD, &num_tasks);
  MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);
  MPI_Get_processor_name(processor_name, &namelen);


  if (my_rank==0)
  {
    MPI_Send(&message, 1, MPI_INT, 1, 0, MPI_COMM_WORLD);
    printf("La tâche %d a envoyé %d\n", my_rank, message);
    MPI_Recv(&message, 1, MPI_INT, num_tasks - 1, 0, MPI_COMM_WORLD, &status);
    printf("La tâche %d a reçu %d\n", my_rank, message);
  }
  else
     {
      MPI_Recv(&message, 1, MPI_INT, my_rank - 1, 0, MPI_COMM_WORLD, &status);
      printf("La tâche %d a reçu %d de %d\n", my_rank, message, my_rank - 1);
      message++;
      MPI_Send(&message, 1, MPI_INT, (my_rank + 1) % num_tasks, 0,MPI_COMM_WORLD);
      printf("La tâche %d a envoyé %d à %d \n", my_rank, message, (my_rank + 1) % num_tasks);
    }

  MPI_Finalize();
}
