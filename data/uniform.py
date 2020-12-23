import numpy as np
import matplotlib.pyplot as plt

from numpy.random import default_rng


NUM_SAMPLES = 1000
DIM = 2

rng = default_rng(1)

# Create samples from a normal (Gaussian) distribution
x = rng.uniform(size=(NUM_SAMPLES, DIM))

plt.scatter(x[:, 0], x[:, 1])
plt.show()

# save the data into csv file with 5 decimals
np.savetxt("uniform.csv", x, delimiter=",", fmt="%.5f")
