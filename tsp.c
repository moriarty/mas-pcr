/*******************************
 * Alex Moriarty, B00317947
 * Login: moriarty
 * CSci2132: Assignment 3
 *******************************/

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

struct node
{
	double x, y;
	int i;
	struct node *next;
	struct node *prev;
};

void plotTour(struct node *tour)
{
/*
*	print postscript code depicting a given tour
*/

    struct node *curr;

    if (tour == NULL)
        return;

    printf("%%!\n/Times-Roman findfont\n15 scalefont\n");
	printf("/s 5 string def\nsetfont\n");
	printf("50 50 translate\n0 0 512 512 rectstroke\n");

        // plot the tour's edges 
        printf("%5.1f %5.1f moveto\n", 512.0*tour->x, 512.0*tour->y);
        printf("1.0 0.0 0.0 setrgbcolor\n");
        for (curr=tour->next; curr != NULL; curr=curr->next)
                printf("%5.1f %5.1f lineto\n",
                        512.0*curr->x, 512.0*curr->y);
        
        printf("closepath stroke\n");

        /* plot dots marking the locations of the nodes */
        printf("0.0 0.0 0.0 setrgbcolor\n");
	int c = 1;
    for (curr=tour; curr!=NULL; curr = curr->next){
        printf("newpath\n");
        printf("%5.1f %5.1f 2 0 360 arc\n",
        512.0*curr->x, 512.0*curr->y);
		printf("%d s cvs show\n", c++);
		printf("(-) show\n");
		printf("%d s cvs show\n", curr->i);		
        printf("closepath\nfill\n");
    }
	printf("showpage\n");
}



struct node *add_node(struct node *list, double x, double y, int i)
{
/*
*	creates and returns a node containing the x and y values given.
*	this node is called new_node, which can the be added to a list.
*/
	struct node *new_node;
	new_node = malloc(sizeof(struct node));
	if (new_node == NULL)
	{
		printf("Error: not enough memory, malloc failed in add_node");
		exit(EXIT_FAILURE);
	}
	new_node->x = x;
	new_node->y = y;
	new_node->i = i;
	new_node->next = list;
	//printf("%p\n",new_node);
	//printf("%p\n",list);
	new_node->prev = NULL;
	if (list != NULL) list->prev = new_node;
	return new_node;
}


struct node *delete(struct node *list, double x, double y, int i)
{
/*
*	finds node in list containing values x and y, then links previous
*	node to the node after the current node and deletes current node.
*/
	struct node *cur, *prev, *next;

	for (cur = list, prev = NULL;
		 cur != NULL && ( cur->x != x && cur->y != x && cur->i != i);
		 prev = cur, cur = cur->next)
		;
	if (cur == NULL){
		// No match found return
		return list;
	}
	if (prev == NULL){
		// First node in list was match
		list = list->next;
	}else{
		if (cur->next != NULL) 
			next = cur->next; 
		if (next != NULL) next->prev = prev; 
		prev->next = cur->next;
	}
	free(cur);
	return list;
}

void move(struct node **from_list, struct node **to_list, double x, double 
y, int i)
{
/*
*	"moves" a node from one list to another. really just creates a new 
*	node in the list its moving it to (to_list) and finds the node in 
*	(from_list) with the matching values and deletes it. 
*/
	*to_list = add_node(*to_list, x, y, i);
	*from_list = delete(*from_list, x, y, i);
}



struct node *get_data(struct node *to_list)
{
/*
*	while two doubles are givin in stdin, it creates a node with those 
* 	two doubles and adds it "to_list".
*/
	double x = 0;
	double y = 0;
	int input;
	int i = 1;

	do{
		input = scanf("%lf%lf", &x, &y);
		to_list = add_node(to_list, x, y, i++);
	}while (input == 2);

	return to_list;
}

double dist_nodes(struct node *point_a, struct node *point_b)
{
/*
*	this finds the distance between two nodes. 
*/
	if ( point_a == point_b ) return 0;
	if ( point_a == NULL || point_b == NULL) return 999999;
	double dx, dy, distance;
	dx = (*point_b).x - (*point_a).x;
	dy = (*point_b).y - (*point_a).y;

	distance = sqrt(dx*dx + dy*dy);
	return distance;
}

double tour_distance(struct node *tour)
{
	if (tour == NULL) return 0.0;

	double distance = 0.0;
	struct node *initial = tour; 
	/** Returns the distance around a tour */
	for (; tour != NULL; tour = tour->next){
		if (tour->next != NULL) {
			distance += dist_nodes(tour, tour->next);
		} else {
			distance += dist_nodes(tour, initial);
		}
	}
	return distance;
}

double swapped_tour_distance(struct node *tour, int A, int B)
{
	if (tour == NULL) return 0.0;
	if ( A == B ) return tour_distance(tour);

	struct node* prt_A; 
	struct node* prt_B;

	double distance = 0.0;
	struct node *initial = tour; 
	/** Returns the distance around a tour */
	int iter;
	for (iter = 0; tour != NULL; tour = tour->next, iter++){
		if ( iter == A) prt_A = tour;
		if ( iter == B) prt_B = tour;
		if (tour->next != NULL) {
			distance += dist_nodes(tour, tour->next);
		} else {
			distance += dist_nodes(tour, initial);
		}
	}
	
	distance -= dist_nodes(prt_A->prev, prt_A);
	distance -= dist_nodes(prt_B->prev, prt_B);
	distance += dist_nodes(prt_B->prev, prt_A);
	distance += dist_nodes(prt_A->prev, prt_B);

	if (prt_A->next == NULL){
		distance -= dist_nodes(prt_A, prt_A->next);
		distance += dist_nodes(prt_B, prt_A->next);
	} else {
		distance -= dist_nodes(prt_A, initial);
		distance += dist_nodes(prt_B, initial);
	}
	if (prt_B->next == NULL){
		distance -= dist_nodes(prt_B, prt_B->next);
		distance += dist_nodes(prt_A, prt_B->next);
	} else {
		distance -= dist_nodes(prt_B, initial);
		distance += dist_nodes(prt_A, initial);
	}



}


struct node *find_closest(struct node *nodes, struct node *tour)
{
/*
*	returns a new node with the closest with the same values as the *	
closest node in nodes from the most recent node in tour.
*	it loops for as long as there is a node in nodes, comparing the  *	
distance between the node in nodes and the last visited node in *	
tour with dist_nodes. 
*	the closets node can the be "moved" into the tour.
*/

	double dist_min = -1, x_min, y_min, dist;
	int i_min = 0;	
	struct node *closest;

	for (; nodes != NULL; nodes = nodes->next)
	{
		dist = dist_nodes(tour, nodes);

		if (dist_min < 0 || dist < dist_min)
		{
			x_min = nodes->x;
			y_min = nodes->y;
			i_min = nodes->i;
			dist_min = dist;
		}
	}

	closest = add_node(closest, x_min, y_min, i_min);

	return closest;
}

int swap(struct node* A, struct node* B)
{
	if(A != NULL && B != NULL){
		struct node* foo_prev; 
		struct node* foo_next;

		foo_prev = A->prev;
		foo_next = A->next;

		A->next = B->next;
		A->prev = B->prev;
		B->next = foo_next;
		B->prev = foo_prev;

		if (A->prev != NULL) A->prev->next = A;
		if (A->next != NULL) A->next->prev = A;

		if (B->prev != NULL) B->prev->next = B;
		if (B->next != NULL) B->next->prev = B;

		return 0;
	} else return 1;
}

struct node* swap_at(struct node* list, int A, int B){
	if (list == NULL) return list;
	if ( A == B ) return list;

	struct node* prt_A; 
	struct node* prt_B;

	struct node *initial = list; 

	int iter;
	for (iter = 0; list != NULL; list = list->next, iter++){
		if ( iter == A) prt_A = list;
		if ( iter == B) prt_B = list;
	}
	iter = swap(prt_A, prt_B);
	if (iter == 0) return list;
	printf("error in swap\n");
	return list;
}


void tour_take_nearest(struct node** nodes, struct node** tour)
{
	//printf("I AM HERE\n");
	struct node *closest = NULL;
	// gets values of the last node in nodes
	double x = (*nodes)->x;
	double y = (*nodes)->y;
	int i = (*nodes)->i;
	// and "moves" it into the tour list
	move(nodes, tour, x, y, i);
	//printf("i \t %d\n",i);
	while (*nodes != NULL )
	{
		// finds closest node, calculates and increments distance, and "moves" 
		//node to tour.
		//printf("%d \n",i);		
		closest = find_closest(*nodes, *tour);
		x = closest->x;
		y = closest->y;
		i = closest->i;
		//printf("i \t %d\n",i);
		move(nodes, tour, x, y, i);
	}
}

double T_(int x){
	double tempurature;
	double variable = 25.0;

	tempurature = exp(-x/variable);
}

int main(void){
/* 	
*	The two required linked lists tour and nodes, aswell as closest *	
which will just hold the values of the closest point from nodes to *	
the last point in tour before its "moved"
*/
	struct node *tour = NULL;
	struct node *nodes = NULL;

	struct node *first = NULL;
	srand(time(NULL));


// gets the data and adds the list to nodes.
	nodes = get_data(nodes);
// last input was taken twice.
	nodes = delete(nodes, nodes->x, nodes->y, nodes->i);
	struct node* k;
	int node_count = 0; 
	for (k = nodes; k != NULL; k=k->next, node_count++ ){
	}
	// these are for calculating the total distance
	double distance = 0;

	//tour_take_nearest(&nodes, &tour);
	//first = tour;

	struct node* current = nodes;
	int r1;
	int r2;
	int iter;
	double x; 
	for (iter = 0; ; iter++){
		//printf ("%d.\n", iter);
		// T_ is an exp func which does not cross zero, 
		if (iter == 10) break;
		distance = tour_distance(nodes);
		r1 = (rand()+1)%node_count;
		r2 = (rand()+1)%node_count;
		//printf("%d\t%d\n",r1, r2);
		//printf("%f\n", distance);
		//printf("distance: %f vs %f\n", distance, swapped_tour_distance(nodes, 4, 5));

		if (distance > swapped_tour_distance(nodes, r1, r2)){
			current = swap_at(nodes, r1, r2);
		} else {
			x = ((double)rand()/(double)RAND_MAX);
			if (T_(iter) > x ){
				current = swap_at(nodes, r1, r2);
			} else {
				// Do nothing. 
			}
		}
	}
	printf("");
	tour = nodes;
	for (; tour != NULL; tour = tour->next){	
		printf("---\ntour\tprev:%p\tcurr:%p\tnext:%p\n",tour->prev,tour, tour->next);
		printf("x = %f \t y = %f \t\t i = %d \n", tour->x, tour->y, tour->i);
	}

/*
	int c;
	struct node* foo;
	foo = tour;
	for ( c = 0; c < 3 && foo!=NULL; c++, foo=foo->next){
		if (c == 2){
			swap(foo, foo->next->next);
		}
	}
*/
	
//prints the distance to standard error
//	printf(stderr, "length: %lf", distance);
//plots the tour	
	//plotTour(nodes);

	return 0;
	
}