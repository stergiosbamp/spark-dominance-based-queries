import numpy as np
import matplotlib.pyplot as plt

from numpy.random import default_rng


NUM_SAMPLES = 1000

rng = default_rng(1)

# The desired mean values
mean = [5, 5]
# The desired covariance matrix
cov = [
    [3.5, 2.6],
    [2.6, 3.5]
]

# Generate the random samples.
x = rng.multivariate_normal(mean, cov, size=NUM_SAMPLES)

# plot
plt.scatter(x[:, 0], x[:, 1])
plt.show()

# save the data into csv file with 5 decimals
np.savetxt("anticorrelation.csv", x, delimiter=",", fmt="%.5f")
