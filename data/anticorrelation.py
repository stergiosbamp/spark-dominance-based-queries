import numpy as np
import matplotlib.pyplot as plt

from numpy.random import default_rng


NUM_SAMPLES = 1000

rng = default_rng(1)

# The desired mean values
MEAN = [1, 1]
# The desired covariance matrix
COV = [
    [3.5, -2.75],
    [-2.75, 3.5]
]

# Generate the random samples.
x = rng.multivariate_normal(MEAN, COV, size=NUM_SAMPLES)

# plot
plt.scatter(x[:, 0], x[:, 1])
plt.show()

# save the data into csv file with 5 decimals
dims = len(MEAN)
np.savetxt("{}d-anticorrelation-{}.csv".format(dims, NUM_SAMPLES), x, delimiter=",", fmt="%.5f")
